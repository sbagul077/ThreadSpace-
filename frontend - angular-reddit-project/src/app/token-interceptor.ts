import { HttpErrorResponse, HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from "@angular/common/http";
import { Auth } from "./auth/shared/auth";
import { BehaviorSubject, catchError, filter, Observable, switchMap, take, throwError } from "rxjs";
import { Injectable } from "@angular/core";
import { fileURLToPath } from "url";
import { LoginResponse } from "./auth/login/login-response.payload";

@Injectable({
    providedIn: 'root'
})

export class TokenInterceptor implements HttpInterceptor{

    isTokenRefreshing = false;
    refreshTokenSubject: BehaviorSubject<any> = new BehaviorSubject(null);

    constructor(public auth: Auth){
    }

    intercept(req: HttpRequest<any>, next: HttpHandler):
     Observable<HttpEvent<any>> {
        
        if (req.url.indexOf('refresh') !== -1 || req.url.indexOf('login') !== -1){
            return next.handle(req);
        }

        const jwtToken= this.auth.getJwtToken();
        // console.log("Interceptor triggered for URL:", req.url);
    // const jwtToken = this.auth.getJwtToken();
        // console.log("Token found:", jwtToken);
        
        // if(jwtToken){
        //     return next.handle(this.addToken(req, jwtToken)).pipe(catchError(error =>{
        //         if(error instanceof HttpErrorResponse
        //             && error.status === 403){
        //                 return this.handleAuthErrors(req, next);
        //         } else {
        //             return throwError (() => error)
        //         }
        //     }));
        // }

        // return next.handle(req);

        if (jwtToken && !req.url.includes('login') && !req.url.includes('refresh')) {
        const cloned = req.clone({
            setHeaders: {
                Authorization: `Bearer ${jwtToken}`
            }
        });
        return next.handle(cloned); // Ensure 'cloned' is passed here!
        }
        return next.handle(req);
    }

    handleAuthErrors(req: HttpRequest<any>, next: HttpHandler): 
        Observable<HttpEvent<any>> {
            if(!this.isTokenRefreshing){
                this.isTokenRefreshing = true;
                this.refreshTokenSubject.next(null);

                return this.auth.refreshToken().pipe(
                    switchMap((refreshTokenResponse : LoginResponse) => {
                        this.isTokenRefreshing = false;
                        this.refreshTokenSubject
                            .next(refreshTokenResponse.authenticationToken);
                        return next.handle(this.addToken(req, 
                            refreshTokenResponse.authenticationToken));
                    })
                )
            } 
            else{
                return this.refreshTokenSubject.pipe(
                    filter(result => result !== null),
                    take(1),
                    switchMap((res) => {
                        return next.handle(this.addToken(req,
                            this.auth.getJwtToken()))
                    })
                );
            }

        }

    addToken(req: HttpRequest<any>, jwtToken: any){
        return req.clone({
            headers: req.headers.set('Authorization', 
                'Bearer ' + jwtToken)
        });
    }
}