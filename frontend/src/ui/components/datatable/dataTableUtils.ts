import type { Column } from '@tanstack/vue-table'

export type DataTableSorting = { id: string; desc: boolean }[]

/** @deprecated Use zod or app schema; kept for API parity with React sample */
export const DataTableSortingSchema = { parse: (v: unknown) => v as DataTableSorting }

export function getCommonPinningStyles<TData>(column: Column<TData, unknown>) {
  const isPinned = column.getIsPinned()
  return isPinned === 'left'
    ? { left: `${column.getStart('left')}px` }
    : isPinned === 'right'
      ? { right: `${column.getStart('right')}px` }
      : {}
}
