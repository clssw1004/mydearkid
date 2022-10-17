<script  lang="ts">
import { defineComponent } from 'vue'
import axios from "axios";
import { Photo } from "../types/Photo"

async function loadList(): Promise<Photo[]> {
    const url = "http://127.0.0.1:17777/api/photo/";
    const response = await axios({ url, method: "GET" });
    if (response.status === 200) {
        return response.data.data as Photo[];
    }
    return [];
}


export default defineComponent({

    data() {
        const list: Photo[] = [];
        return {
            list,
        }
    },
    async mounted() {
        this.list = await loadList();
    }
})

</script>
<template>
    <!-- <el-image style="width: 100px; height: 100px" :src="url" fit="fill" /> -->
</template>