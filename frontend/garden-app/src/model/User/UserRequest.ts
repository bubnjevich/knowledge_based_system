
export interface UserRequest {
    id?: number,
    name: string,
    lastName: string,
    email: string,
    password: string
}

export interface LoginRequest {
    email: string,
    password: string
}