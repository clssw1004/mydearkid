import { createRouter, createWebHashHistory } from "vue-router";
import GalleryWall from "../components/GalleryWall.vue";

const routes = [
    { path: '/gallery', component: GalleryWall, name: "gallery", default: true },
]


const router = createRouter({
    history: createWebHashHistory(),
    routes,
})

export default router;