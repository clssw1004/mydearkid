export interface CommonResponse<T> {
    code: 0 | 500;
    message: 'ok' | string;
    data: T
}

export interface Page<T> {
    content: T[]
    totalPages?: number;
    totalElements?: number;
    size: number;
}

export interface PageRequest {
    page: number;
    pagesize: number;
    condition?: any;
}