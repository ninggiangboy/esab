<script setup lang="ts">
import type { ColumnDef } from '@tanstack/vue-table'
import { computed, ref } from 'vue'

import UiDataTable from '@/components/ui/UiDataTable.vue'
import type { DataTableSorting } from '@/components/ui/data-table-utils'
import { PAYMENT_ROWS } from '@/lib/docs/payments-demo'

import { paymentColumns } from './paymentColumns'

type Identifiable = { id: string | number }

const columns = paymentColumns() as ColumnDef<Identifiable, unknown>[]

const sorting = ref<DataTableSorting | null>(null)

const displayData = computed(() => {
  const rows = [...PAYMENT_ROWS]
  const s = sorting.value
  if (!s?.sortBy) return rows
  const { sortBy, sortDirection } = s
  return rows.sort((a, b) => {
    const av = String(a[sortBy as keyof typeof a] ?? '')
    const bv = String(b[sortBy as keyof typeof b] ?? '')
    const c = av.localeCompare(bv)
    return sortDirection === 'desc' ? -c : c
  })
})
</script>

<template>
  <div class="w-full space-y-3">
    <UiDataTable
      v-model:sorting="sorting"
      enable-sorting
      :columns="columns"
      :data="displayData"
      container-class-name="h-[310px]"
    />
  </div>
</template>
