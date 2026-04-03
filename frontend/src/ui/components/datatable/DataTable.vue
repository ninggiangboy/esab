<script setup lang="ts" generic="TData extends { id: string | number }">
import type { ColumnDef } from '@tanstack/vue-table'
import { FlexRender, getCoreRowModel, getSortedRowModel, useVueTable } from '@tanstack/vue-table'
import { ArrowDown, ArrowUp, FileSearch } from 'lucide-vue-next'
import { toRef } from 'vue'
import { Spinner } from '@/ui/components/spinner'
import { Table, TableBody, TableCell, TableHead, TableHeader, TableRow } from '@/ui/components/table'
import { cn } from '@/ui/lib/utils'
import { getCommonPinningStyles } from './dataTableUtils'

const props = withDefaults(
  defineProps<{
    data: TData[]
    columns: ColumnDef<TData, unknown>[]
    containerClassName?: string
    enableSorting?: boolean
    isLoading?: boolean
  }>(),
  {
    enableSorting: false,
    isLoading: false,
  },
)

const dataRef = toRef(props, 'data')

const table = useVueTable({
  get data() {
    return dataRef.value
  },
  get columns() {
    return props.columns
  },
  enableSorting: props.enableSorting,
  getCoreRowModel: getCoreRowModel(),
  getSortedRowModel: getSortedRowModel(),
})
</script>

<template>
  <Table :container-class-name="containerClassName">
    <TableHeader>
      <TableRow v-for="headerGroup in table.getHeaderGroups()" :key="headerGroup.id">
        <TableHead
          v-for="header in headerGroup.headers"
          :key="header.id"
          :style="{ ...getCommonPinningStyles(header.column), width: header.getSize() ? `${header.getSize()}px` : undefined }"
          :class="(header.column.columnDef as { meta?: { className?: string } }).meta?.className"
        >
          <div v-if="!header.isPlaceholder" class="flex items-center gap-1">
            <button
              v-if="header.column.getCanSort()"
              type="button"
              class="inline-flex items-center gap-1"
              @click="header.column.getToggleSortingHandler()?.($event)"
            >
              <FlexRender :render="header.column.columnDef.header" :props="header.getContext()" />
              <ArrowUp
                v-if="header.column.getIsSorted() === 'asc'"
                class="size-3.5 opacity-60"
              />
              <ArrowDown
                v-else-if="header.column.getIsSorted() === 'desc'"
                class="size-3.5 opacity-60"
              />
            </button>
            <FlexRender
              v-else
              :render="header.column.columnDef.header"
              :props="header.getContext()"
            />
          </div>
        </TableHead>
      </TableRow>
    </TableHeader>
    <TableBody>
      <template v-if="isLoading">
        <TableRow>
          <TableCell :colspan="columns.length" class="h-24">
            <div class="flex justify-center py-6">
              <Spinner class="text-muted-foreground" />
            </div>
          </TableCell>
        </TableRow>
      </template>
      <template v-else-if="table.getRowModel().rows.length">
        <TableRow
          v-for="row in table.getRowModel().rows"
          :key="row.id"
          :data-state="row.getIsSelected() ? 'selected' : undefined"
        >
          <TableCell
            v-for="cell in row.getVisibleCells()"
            :key="cell.id"
            :style="{ ...getCommonPinningStyles(cell.column), width: cell.column.getSize() ? `${cell.column.getSize()}px` : undefined }"
            :class="(cell.column.columnDef as { meta?: { className?: string } }).meta?.className"
          >
            <FlexRender :render="cell.column.columnDef.cell" :props="cell.getContext()" />
          </TableCell>
        </TableRow>
      </template>
      <TableRow v-else>
        <TableCell :colspan="columns.length" class="h-24 text-center">
          <div :class="cn('flex flex-col items-center justify-center gap-2 text-muted-foreground py-8')">
            <FileSearch class="size-8 opacity-50" />
            <span class="text-sm">No data</span>
          </div>
        </TableCell>
      </TableRow>
    </TableBody>
  </Table>
</template>
