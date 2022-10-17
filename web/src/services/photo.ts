import axios from "axios";
import { Page, PageRequest } from "../types/common";
import { Photo } from "../types/Photo";

export const getPage = async (pageRequest: PageRequest): Promise<Page<Photo>> => {
    const url = "http://127.0.0.1:17777/api/photo/";
    const response = await axios({ url, method: "GET" });
    if (response.status === 200) {
        return response.data.data as Page<Photo>;
    }
    return { size: 0, content: [] };
}