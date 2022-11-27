
export interface Photo {
    uid: string;
    fileLength: number;
    uploadTime: number[];
    fid: string;
    eventId: string;
    remark: string;
    path: string;
    takeTime: string;
}

export interface PhotoGroup {
    date: string;
    photos: Photo[];
}

export interface PhotoYearDistribute {
    year: string;
    count: number;
}