<template>
    <div class="gallery-title">
        <h1>这个是标题</h1>
    </div>
    <div class="gallery-time">
        <template v-for="item in years">
            <el-button :type="selectYear == item.year ? 'primary' : undefined" @click="() => {
                getPhotos(item.year)
            }" class="tag-year">{{ item.year }}&nbsp;({{ item.count }})</el-button>
        </template>
    </div>

    <div class="gallery-scale">
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
import { PhotoGroup, PhotoYearDistribute } from "../types/Photo"
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
            this.selectYear = y;
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

.gallery-title {
    text-align: center;
}

.gallery-time {
    text-align: center;
}

.gallery-scale {
    text-align: right;
}
</style>