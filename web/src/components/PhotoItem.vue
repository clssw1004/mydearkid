<script setup lang="ts">
import { ref } from 'vue'
import { Photo } from '../types/Photo';
defineProps<{ photo: Photo, scale: String }>()

</script>

<template>
    <div class="img-box" :body-style="{ padding: '0px' }">
        <el-image class="image-item" :src="`http://127.0.0.1:17777/api/photo/${scale || 'middle'}/${photo.fid}`"
            fit="fill" />
        <!-- <div v-if="photo.takeTime">{{photo.takeTime}}</div> -->
    </div>
</template>

<style scoped>
.img-box {
    display: inline-block;
    margin: 0 5px;
    background: transparent;
    border: 0;
}

.image-item {}
</style>
