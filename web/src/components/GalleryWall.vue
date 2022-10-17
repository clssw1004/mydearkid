<script  lang="ts">
import { defineComponent } from 'vue'
import { Photo } from "../types/Photo"
import { getPage } from "../services/photo";
export default defineComponent({
    data() {
        const list: Photo[] = [];
        return {
            list,
        }
    },
    async mounted() {
        const page = await getPage({ page: 1, pagesize: 20 });
        if (page.size > 0) {
            this.list = page.content;
        }
    }
})

</script>
<template>
    <div class="gallery-box">
        <el-image v-for="photo of list" class="img-item" :src="`http://127.0.0.1:17777/api/photo/view/${photo.fid}`"
            fit="fill" />
    </div>
</template>

<style scoped>
.gallery-box {
    float: left;
}

.img-item {
    width: 200px;
    height: 200px;
    display: inline-block;
}

.img-item img {}
</style>