<script setup lang="ts">
import { ref } from 'vue'
import { Photo } from '../types/Photo';
defineProps<{ photo: Photo }>()

</script>

<template>
    <el-card class="img-item" :body-style="{ padding: '0px' }">
        <el-image :src="`http://127.0.0.1:17777/api/photo//thumbnail/${photo.fid}`" fit="fill" />
        <div v-if="photo.takeTime">{{photo.takeTime}}</div>
    </el-card>
</template>

<style scoped>
.gallery-box {
    float: left;
}

.img-item {
    width: 200px;
    height: 200px;
    display: inline-block;
    margin: 0 5px;
}
</style>
