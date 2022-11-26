import axios from "axios";
import { PageRequest } from "../types/common";
import { PhotoGroup } from "../types/Photo";

export const getPhotoPage = async (pr: PageRequest): Promise<PhotoGroup[]> => {
    const url = `http://127.0.0.1:17777/api/photo/groupWith?g=${pr.g}`;
    const response = await axios({ url, method: "GET" });
    if (response.status === 200) {
        return response.data.data as PhotoGroup[];
    }
    return [];
}