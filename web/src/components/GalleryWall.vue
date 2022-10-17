<script  lang="ts">
import { defineComponent } from 'vue'
import { Photo } from "../types/Photo"
import { getPage } from "../services/photo";
import PhotoItem from './PhotoItem.vue'
import PhotoItem from './PhotoItem.vue'
export default defineComponent({
    data() {
        const list: Photo[] = [];
        return {
            list,
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
    <div class="gallery-box">
        <PhotoItem v-for="photo of list" :photo="photo" />
    </div>
</template>

<style scoped>
.gallery-box {
    width: 100%;
    float: left;
}

</style>