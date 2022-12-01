import { createRouter, createWebHistory } from "vue-router";

const GalleryWall = () => import('../components/GalleryWall.vue')

const routes = [
    { path: '/gallery', component: GalleryWall, name: "gallery", default: true },
]


const router = createRouter({
    history: createWebHistory(),
    routes,
})

export default router;