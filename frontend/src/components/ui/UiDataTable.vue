<script setup lang="ts">
import {
  FlexRender,
  getCoreRowModel,
  useVueTable,
  type ColumnDef,
  type ColumnPinningState,
  type RowSelectionState,
  type SortingState,
} from '@tanstack/vue-table'
import { ArrowDown, ArrowUp, FileSearch } from 'lucide-vue-next'
import type { Updater } from '@tanstack/vue-table'
import { computed, ref, watch } from 'vue'

import { cn } from '@/lib/utils'

import type { DataTableSorting } from './data-table-utils'
import { getCheckboxColumnDef, getCommonPinningStyles } from './data-table-utils'
import UiSpinner from './UiSpinner.vue'
import UiTable from './UiTable.vue'
import UiTableBody from './UiTableBody.vue'
import UiTableCell from './UiTableCell.vue'
import UiTableHead from './UiTableHead.vue'
import UiTableHeader from './UiTableHeader.vue'
import UiTableRow from './UiTableRow.vue'

interface Identifiable {
  id: string | number
}

const props = withDefaults(
  defineProps<{
    columns: ColumnDef<Identifiable, unknown>[]
    data: Identifiable[]
    containerClassName?: string
    enableSorting?: boolean
    sorting?: DataTableSorting | null
    enableRowSelection?: boolean
    rowSelection?: RowSelectionState
    isLoading?: boolean
    columnPinning?: ColumnPinningState
  }>(),
  {
    enableSorting: false,
    isLoading: false,
  },
)

const emit = defineEmits<{
  'update:sorting': [value: DataTableSorting | null]
  'update:rowSelection': [value: RowSelectionState | Updater<RowSelectionState>]
}>()

const localSorting = ref<DataTableSorting | null>(props.sorting ?? null)
watch(
  () => props.sorting,
  (v) => {
    localSorting.value = v ?? null
  },
)

const sorting = computed({
  get: () => props.sorting ?? localSorting.value,
  set: (v) => {
    localSorting.value = v
    emit('update:sorting', v)
  },
})

function sortingState(): SortingState {
  const s = sorting.value
  return s?.sortBy ? [{ id: s.sortBy, desc: s.sortDirection === 'desc' }] : []
}

function onSortingChange(updater: Updater<SortingState>) {
  const old: SortingState = sortingState()
  const next = typeof updater === 'function' ? updater(old) : updater
  if (next[0]?.id) {
    sorting.value = {
      sortBy: next[0]!.id,
      sortDirection: next[0]!.desc ? 'desc' : 'asc',
    }
  } else {
    sorting.value = null
  }
}

const columnsWithSelect = computed(() => {
  if (!props.enableRowSelection) return props.columns
  return [getCheckboxColumnDef<Identifiable>(), ...props.columns]
})

const rowSelectionSync = computed({
  get: () => props.rowSelection ?? {},
  set: (v) => emit('update:rowSelection', v),
})

const table = useVueTable({
  get data() {
    return props.data
  },
  get columns() {
    return columnsWithSelect.value
  },
  getCoreRowModel: getCoreRowModel(),
  manualSorting: true,
  enableSorting: props.enableSorting,
  enableRowSelection: props.enableRowSelection,
  onRowSelectionChange: (updater) => {
    const next = typeof updater === 'function' ? updater(rowSelectionSync.value) : updater
    emit('update:rowSelection', next)
  },
  onSortingChange,
  getRowId: (row) => String(row.id),
  state: {
    get rowSelection() {
      return props.rowSelection ?? rowSelectionSync.value
    },
    get sorting() {
      return sortingState()
    },
  },
  defaultColumn: { size: 180 },
  initialState: {
    columnPinning: props.columnPinning ?? {
      left: ['select'],
      right: ['actions'],
    },
  },
})
</script>

<template>
  <UiTable :container-class="cn('relative', containerClassName)">
    <UiTableHeader>
      <UiTableRow v-for="headerGroup in table.getHeaderGroups()" :key="headerGroup.id">
        <UiTableHead
          v-for="header in headerGroup.headers"
          :key="header.id"
          :style="{
            minWidth: `${header.column.columnDef.size}px`,
            maxWidth: `${header.column.columnDef.size}px`,
          }"
          :class="
            cn(getCommonPinningStyles(header.column), header.column.columnDef.meta?.className)
          "
        >
          <template v-if="!header.isPlaceholder">
            <div
              :class="
                header.column.getCanSort()
                  ? 'cursor-pointer select-none inline-flex items-center gap-0.5'
                  : ''
              "
              @click="header.column.getToggleSortingHandler()?.($event)"
            >
              <FlexRender :render="header.column.columnDef.header" :props="header.getContext()" />
              <span v-if="header.column.getCanSort()" class="inline-block -translate-y-px">
                <ArrowUp v-if="header.column.getIsSorted() === 'asc'" class="inline-block size-4" />
                <ArrowDown
                  v-else-if="header.column.getIsSorted() === 'desc'"
                  class="inline-block size-4"
                />
                <ArrowUp v-else class="inline-block size-4 opacity-0" />
              </span>
            </div>
          </template>
        </UiTableHead>
      </UiTableRow>
    </UiTableHeader>
    <UiTableBody>
      <template v-if="table.getRowModel().rows.length">
        <UiTableRow
          v-for="row in table.getRowModel().rows"
          :key="row.id"
          :data-state="row.getIsSelected() ? 'selected' : undefined"
        >
          <UiTableCell
            v-for="cell in row.getVisibleCells()"
            :key="cell.id"
            :style="{
              minWidth: `${cell.column.columnDef.size}px`,
              maxWidth: `${cell.column.columnDef.size}px`,
            }"
            :class="cn(getCommonPinningStyles(cell.column), cell.column.columnDef.meta?.className)"
            :title="String(cell.getValue() ?? '')"
          >
            <FlexRender :render="cell.column.columnDef.cell" :props="cell.getContext()" />
          </UiTableCell>
        </UiTableRow>
      </template>
      <tr v-else class="relative h-20">
        <td
          colspan="999"
          class="absolute inset-0 top-10 flex flex-col items-center justify-center gap-2"
        >
          <UiSpinner v-if="isLoading" class="size-6 text-primary" />
          <template v-else>
            <div class="rounded-lg bg-background-tertiary p-3">
              <FileSearch class="size-8 text-muted-foreground" />
            </div>
            <span class="text-base font-medium text-foreground">No results</span>
          </template>
        </td>
      </tr>
    </UiTableBody>
  </UiTable>
</template>
