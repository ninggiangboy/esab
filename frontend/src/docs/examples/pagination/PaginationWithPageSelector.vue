<script setup lang="ts">
import { ref } from 'vue'
import { PaginationBar } from '@/ui/components/pagination'

const page = ref(1)
const pageSize = ref(5)
const total = 100
</script>

<template>
  <div class="flex w-full items-center justify-between gap-2 rounded-lg border p-4">
    <PaginationBar
      :page="page"
      :page-size="pageSize"
      :total="total"
      :page-size-options="[
        { id: '5', name: '5 / page' },
        { id: '10', name: '10 / page' },
        { id: '20', name: '20 / page' },
      ]"
      @update:page="page = $event"
      @update:page-size="pageSize = $event"
    />
  </div>
</template>
