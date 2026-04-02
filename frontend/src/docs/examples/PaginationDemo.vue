<script setup lang="ts">
import { ref } from 'vue'

import UiPagination from '@/components/ui/UiPagination.vue'

const page = ref(1)
</script>

<template>
  <UiPagination v-model="page" :page-count="10" />
</template>
