<script  lang="ts">
import { defineComponent } from 'vue'
import { Photo } from "../types/Photo"
import { getPage } from "../services/photo";
import PhotoItem from './PhotoItem.vue'
export default defineComponent({
    data() {
        const list: Photo[] = [];
        return {
            list,
            reverse: false
        };
    },
    async mounted() {
        const page = await getPage({ page: 1, pagesize: 20 });
        if (page.size > 0) {
            this.list = page.content;
        }
    },
    components: { PhotoItem }
})

</script>
<template>
    <!-- <div class="gallery-box"> -->
    <el-timeline :reverse="reverse">
        <el-timeline-item v-for="photo of list" :key="photo.uid" :timestamp="photo.takeTime">
            <PhotoItem :photo="photo" />
        </el-timeline-item>
    </el-timeline>
    <!-- </div> -->
</template>

<style scoped>
.gallery-box {
    width: 100%;
    float: left;
}
</style>