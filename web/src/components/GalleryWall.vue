<script  lang="ts">
import { defineComponent } from 'vue'
import { Photo, PhotoGroup } from "../types/Photo"
import { getPhotoPage } from "../services/photo";
import PhotoItem from './PhotoItem.vue'
export default defineComponent({
    data() {
        const list: PhotoGroup[] = [];
        return {
            list,
            reverse: false
        };
    },
    async mounted() {
        const page = await getPhotoPage({ g: "yyyy-MM" });
        if (page.length > 0) {
            this.list = page;
        }
    },
    components: { PhotoItem }
})

</script>
<template>
    <el-timeline :reverse="reverse">
        <el-timeline-item v-for="{ date, photos } in list" :key="date" :timestamp="date" :hide-timestamp="true">
            <h2>{{ date }}</h2>
            <PhotoItem v-for="photo of photos" :photo="photo" />
        </el-timeline-item>
    </el-timeline>
</template>

<style scoped>

</style>