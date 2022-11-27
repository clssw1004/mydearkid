import axios from "axios";
import { PageRequest } from "../types/common";
import { PhotoGroup, PhotoYearDistribute } from "../types/Photo";

export const getPhotoPage = async (pr: PageRequest): Promise<PhotoGroup[]> => {
    const url = `http://127.0.0.1:17777/api/photo/groupWith`;
    const response = await axios({ url, method: "POST", data: pr });
    if (response.status === 200) {
        return response.data.data as PhotoGroup[];
    }
    return [];
}

export const getYears = async (): Promise<PhotoYearDistribute[]> => {
    const url = `http://127.0.0.1:17777/api/photo/years`;
    const response = await axios({ url, method: "GET" });
    if (response.status === 200) {
        console.log(response.data.data);
        return response.data.data as PhotoYearDistribute[];
    }
    return [];
}