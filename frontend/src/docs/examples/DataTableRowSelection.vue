<script setup lang="ts">
import type { ColumnDef } from '@tanstack/vue-table'
import type { RowSelectionState } from '@tanstack/vue-table'
import { computed, ref } from 'vue'

import UiButton from '@/components/ui/UiButton.vue'
import UiDataTable from '@/components/ui/UiDataTable.vue'

interface UserRow {
  id: string
  name: string
  email: string
  department: string
}

const data: UserRow[] = [
  { id: '1', name: 'Alice Johnson', email: 'alice@company.com', department: 'Engineering' },
  { id: '2', name: 'Bob Smith', email: 'bob@company.com', department: 'Marketing' },
  { id: '3', name: 'Carol Davis', email: 'carol@company.com', department: 'Engineering' },
  { id: '4', name: 'David Wilson', email: 'david@company.com', department: 'Sales' },
  { id: '5', name: 'Eva Brown', email: 'eva@company.com', department: 'HR' },
]

type Identifiable = { id: string | number }

const columns = [
  { accessorKey: 'name', header: 'Name' },
  { accessorKey: 'email', header: 'Email' },
  { accessorKey: 'department', header: 'Department' },
] as ColumnDef<Identifiable, unknown>[]

const rowSelection = ref<RowSelectionState>({})

const selectedCount = computed(() => Object.keys(rowSelection.value).length)

function bulk(action: string) {
  window.alert(`${action} ${selectedCount.value} selected users`)
}
</script>

<template>
  <div class="w-full space-y-3">
    <div class="flex h-8 items-center justify-between">
      <span class="text-sm text-muted-foreground"
        >{{ selectedCount }} of {{ data.length }} selected</span
      >
      <div v-if="selectedCount > 0" class="flex gap-2">
        <UiButton variant="destructive" type="button" @click="bulk('Delete')"
          >Delete selected</UiButton
        >
        <UiButton variant="outline" type="button" @click="bulk('Export')">Export selected</UiButton>
      </div>
    </div>
    <UiDataTable
      v-model:row-selection="rowSelection"
      enable-row-selection
      :columns="columns"
      :data="data"
    />
  </div>
</template>
