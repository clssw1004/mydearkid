<template>

    <h1>这个是标题</h1>
    <div>
        <template v-for="year in years">
            <el-button @click="() => {
                getPhotos(year.year)
            }" class="tag-year">{{ year.year }}</el-button>
        </template>
    </div>

    <div>
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

<script  lang="ts">
import { defineComponent } from 'vue'
import { Photo, PhotoGroup, PhotoYearDistribute } from "../types/Photo"
import { getPhotoPage, getYears } from "../services/photo";
import PhotoItem from './PhotoItem.vue'
import { ElRadioGroup, ElRadioButton, ElTimeline, ElTimelineItem } from 'element-plus';
import moment from "moment";

export default defineComponent({
    data() {
        const list: PhotoGroup[] = [];
        const years: PhotoYearDistribute[] = [];
        const selectYear: string = moment().format("yyyy");
        return {
            list,
            scale: "medium",
            years,
            reverse: false,
            selectYear
        };
    },
    async mounted() {
        await this.loadYears();
    },
    methods: {
        async getPhotos(y: string) {
            const photos = await getPhotoPage({ g: "yyyy-MM", y });
            console.log(y)
            if (photos.length > 0) {
                this.list = photos;
            } else {
                this.list = [];
            }
        },
        async loadYears() {
            this.years = await getYears();
            if (this.years.length > 0) {
                this.getPhotos(this.years[this.years.length - 2].year)
            }
        },
    },
    components: { PhotoItem }
})

</script>
<style scoped>
.tag-year {
    margin: 10px;
}
</style>