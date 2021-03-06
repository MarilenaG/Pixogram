export class User {
    id: number;
    userName: string;
    password: string;
    email:string;
    registrationCode: string;
    active: boolean;
    imageDownloadPath: string;
    defaultImageDownloadUrl:string;
    followedByMe:boolean;
    followingMe:boolean;
}