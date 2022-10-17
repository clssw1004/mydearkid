<script setup lang="ts">
import { ref } from 'vue'
import { Photo } from '../types/Photo';

defineProps<{ photo: Photo }>()

</script>

<template>
    <el-image class="img-item" :src="`http://127.0.0.1:17777/api/photo/view/${photo.fid}`" fit="fill" />
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
