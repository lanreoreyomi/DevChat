export declare interface IUSERINFO {
    userName: string
    firstName: string;
    lastName: string;
    email: string;
    details?: IUSERDETAILS;
    address?:IUSERADDRESS;
}

export interface IUSERDETAILS {
    about: string;
    birthday: string;
    education: string,
    occupation: string,
    phone: number
}

export interface IUSERADDRESS {
    addressLine1: string;
    city: string;
    country: string;
    state: string;
    zipcode: string
}