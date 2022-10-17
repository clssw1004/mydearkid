<script setup lang="ts">
import { ref } from 'vue'

// defineProps<{}>()
const fits = ['fill', 'contain', 'cover', 'none', 'scale-down']
const url =
    'https://fuss10.elemecdn.com/e/5d/4a731a90594a4af544c0c25941171jpeg.jpeg'

</script>
<template>
    <el-image style="width: 100px; height: 100px" :src="url" :fit="fit" />
</template>