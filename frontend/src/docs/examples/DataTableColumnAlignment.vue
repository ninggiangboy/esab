<script setup lang="ts">
import type { ColumnDef } from '@tanstack/vue-table'

import UiDataTable from '@/components/ui/UiDataTable.vue'

interface UserRow {
  id: string
  name: string
  email: string
  balance: string
}

const data: UserRow[] = [
  { id: '1', name: 'John Doe', email: 'john@example.com', balance: '$1,000' },
  { id: '2', name: 'Jane Smith', email: 'jane@example.com', balance: '$2,000' },
  { id: '3', name: 'Bob Johnson', email: 'bob@example.com', balance: '$3,000' },
]

type Identifiable = { id: string | number }

const columns = [
  { accessorKey: 'name', header: 'Name' },
  { accessorKey: 'email', header: 'Email' },
  {
    accessorKey: 'balance',
    header: 'Balance',
    meta: { className: 'text-right' },
  },
] as ColumnDef<Identifiable, unknown>[]
</script>

<template>
  <div class="w-full">
    <UiDataTable :columns="columns" :data="data" />
  </div>
</template>
