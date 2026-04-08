import { ApplicationConfig, provideBrowserGlobalErrorListeners } from '@angular/core';
import { provideRouter } from '@angular/router';
// import { provideNgxWebstorage } from 'ngx-webstorage';
import { routes } from './app.routes';
import {  withEventReplay } from '@angular/platform-browser';
import { provideHttpClient, withFetch } from '@angular/common/http';
import { provideToastr } from 'ngx-toastr';
import { provideNgxWebstorage, withLocalStorage } from 'ngx-webstorage';

export const appConfig: ApplicationConfig = {
  providers: [
    provideBrowserGlobalErrorListeners(),
    provideRouter(routes), 

    // provideNgxWebstorage(),
    provideNgxWebstorage(withLocalStorage()),
    provideHttpClient(withFetch()),
    provideToastr() 
  ]
};
