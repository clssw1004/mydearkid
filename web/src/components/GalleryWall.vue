<script  lang="ts">
import { defineComponent } from 'vue'
import { Photo, PhotoGroup } from "../types/Photo"
import { getPhotoPage, getYears } from "../services/photo";
import PhotoItem from './PhotoItem.vue'
export default defineComponent({
    data() {
        const list: PhotoGroup[] = [];
        return {
            list,
            scale: "medium",
            reverse: false
        };
    },
    async mounted() {
        await this.loadYears();
    },
    methods: {
        async getPhotos() {
            const page = await getPhotoPage({ g: "yyyy-MM" });
            if (page.length > 0) {
                this.list = page;
            }
        },
        async loadYears() {
            const years = await getYears();
        }

    },
    components: { PhotoItem }
})

</script>
<template>
    <div>
        <h1>这个是标题</h1>
        <el-radio-group v-model="scale" size="mini">
            <el-radio-button label="minimum">最小</el-radio-button>
            <el-radio-button label="mini">小</el-radio-button>
            <el-radio-button label="medium">中</el-radio-button>
            <el-radio-button label="large">大</el-radio-button>
            <el-radio-button label="largest">最大</el-radio-button>
        </el-radio-group>
    </div>
    <el-timeline :reverse="reverse">
        <el-timeline-item v-for="{ date, photos } in list" :key="date" :timestamp="date" :hide-timestamp="true">
            <h2>{{ date }}</h2>
            <PhotoItem v-for="photo of photos" :photo="photo" :scale="scale" />
        </el-timeline-item>
    </el-timeline>
</template>

<style scoped>

</style>