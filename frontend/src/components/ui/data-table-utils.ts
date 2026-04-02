import type { Column, ColumnDef } from '@tanstack/vue-table'
import { h } from 'vue'
import { z } from 'zod'

import { cn } from '@/lib/utils'

import UiCheckbox from './UiCheckbox.vue'

export const DataTableSortingSchema = z.object({
  sortBy: z.string(),
  sortDirection: z.enum(['asc', 'desc']),
})

export type DataTableSorting = z.infer<typeof DataTableSortingSchema>

export function getCheckboxColumnDef<T>(): ColumnDef<T> {
  return {
    id: 'select',
    header: ({ table }) =>
      h(UiCheckbox, {
        checked: table.getIsAllRowsSelected()
          ? true
          : table.getIsSomePageRowsSelected()
            ? 'indeterminate'
            : false,
        'onUpdate:checked': (v: boolean | 'indeterminate') =>
          table.toggleAllRowsSelected(v === true),
        'aria-label': 'Select all',
      }),
    cell: ({ row }) =>
      h(UiCheckbox, {
        checked: row.getIsSelected(),
        disabled: !row.getCanSelect(),
        'onUpdate:checked': (v: boolean | 'indeterminate') => row.toggleSelected(v === true),
        'aria-label': 'Select row',
      }),
    size: 42,
    enableSorting: false,
  }
}

export function getCommonPinningStyles<T>(column: Column<T>): string {
  const isPinned = column.getIsPinned()
  const isLastLeftPinnedColumn = isPinned === 'left' && column.getIsLastColumn('left')
  const isFirstRightPinnedColumn = isPinned === 'right' && column.getIsFirstColumn('right')

  return cn(
    isPinned === 'left' && 'sticky left-0',
    isPinned === 'right' && 'sticky right-0',
    isPinned ? 'z-[1]' : 'z-0',
    isLastLeftPinnedColumn &&
      'group-data-[at-start="false"]:shadow-[-6px_0_6px_-6px_oklch(0_0_0_/_0.15)_inset] dark:group-data-[at-start="false"]:shadow-[-6px_0_6px_-6px_oklch(0_0_0_/_0.3)_inset]',
    isFirstRightPinnedColumn &&
      'group-data-[at-end="false"]:shadow-[6px_0_6px_-6px_oklch(0_0_0_/_0.15)_inset] dark:group-data-[at-end="false"]:shadow-[6px_0_6px_-6px_oklch(0_0_0_/_0.3)_inset]',
  )
}

declare module '@tanstack/vue-table' {
  interface ColumnMeta<TData, TValue> {
    className?: string
  }
}
