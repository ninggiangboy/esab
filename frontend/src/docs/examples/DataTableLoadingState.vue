<script setup lang="ts">
import type { ColumnDef } from '@tanstack/vue-table'
import { ref } from 'vue'

import UiButton from '@/components/ui/UiButton.vue'
import UiDataTable from '@/components/ui/UiDataTable.vue'
import UiSpinner from '@/components/ui/UiSpinner.vue'

interface ProductRow {
  id: string
  name: string
  category: string
  price: number
}

const data: ProductRow[] = [
  { id: '1', name: 'Laptop Pro', category: 'Electronics', price: 1299.99 },
  { id: '2', name: 'Wireless Mouse', category: 'Accessories', price: 29.99 },
  { id: '3', name: 'Mechanical Keyboard', category: 'Accessories', price: 149.99 },
]

type Identifiable = { id: string | number }

const columns = [
  { accessorKey: 'name', header: 'Product Name' },
  { accessorKey: 'category', header: 'Category' },
  {
    accessorKey: 'price',
    header: 'Price',
    cell: ({ getValue }) => `$${Number(getValue()).toFixed(2)}`,
    meta: { className: 'text-right' },
  },
] as ColumnDef<Identifiable, unknown>[]

const isLoading = ref(false)

function simulateLoad() {
  isLoading.value = true
  setTimeout(() => {
    isLoading.value = false
  }, 1000)
}
</script>

<template>
  <div class="w-full space-y-4">
    <div class="flex gap-2">
      <UiButton variant="outline" :disabled="isLoading" @click="simulateLoad">
        <UiSpinner v-if="isLoading" class="size-4" />
        Simulate initial load
      </UiButton>
    </div>
    <UiDataTable
      :columns="columns"
      :data="isLoading ? [] : data"
      :is-loading="isLoading"
      container-class-name="h-[180px]"
    />
  </div>
</template>
