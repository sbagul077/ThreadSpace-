import { VoteType } from "./vote-type";


export class VotePayLoad{
    voteType: VoteType | undefined;
    postId: number | undefined;
}