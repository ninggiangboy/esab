<script setup lang="ts">
import { computed, ref } from 'vue'

import UiNativeSelect from '@/components/ui/UiNativeSelect.vue'
import UiPagination from '@/components/ui/UiPagination.vue'

const page = ref(1)
const pageSize = ref(5)

const pageCount = computed(() => Math.max(1, Math.ceil(42 / pageSize.value)))

function onSize(ev: Event) {
  pageSize.value = Number((ev.target as HTMLSelectElement).value)
  page.value = 1
}
</script>

<template>
  <div class="flex w-full flex-wrap items-center justify-between gap-2 rounded-lg border p-4">
    <UiNativeSelect class="w-28" :value="String(pageSize)" @change="onSize">
      <option value="5">5 / page</option>
      <option value="10">10 / page</option>
      <option value="20">20 / page</option>
    </UiNativeSelect>
    <UiPagination v-model="page" :page-count="pageCount" />
  </div>
</template>
