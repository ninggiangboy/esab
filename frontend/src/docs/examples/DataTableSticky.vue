<script setup lang="ts">
import type { ColumnDef } from '@tanstack/vue-table'
import { Pencil, Trash2 } from 'lucide-vue-next'
import { h } from 'vue'

import UiButton from '@/components/ui/UiButton.vue'
import UiDataTable from '@/components/ui/UiDataTable.vue'

interface OrderRow {
  id: string
  orderNumber: string
  customer: string
  email: string
  product: string
  quantity: number
  price: number
  orderDate: string
  total: number
}

const data: OrderRow[] = [
  {
    id: '1',
    orderNumber: 'ORD-001',
    customer: 'John Smith',
    email: 'john@example.com',
    product: 'Laptop Pro 15"',
    quantity: 1,
    price: 1299.99,
    orderDate: '2024-01-15',
    total: 1299.99,
  },
  {
    id: '2',
    orderNumber: 'ORD-002',
    customer: 'Sarah Johnson',
    email: 'sarah@example.com',
    product: 'Wireless Mouse',
    quantity: 2,
    price: 29.99,
    orderDate: '2024-01-16',
    total: 59.98,
  },
  {
    id: '3',
    orderNumber: 'ORD-003',
    customer: 'Mike Wilson',
    email: 'mike@example.com',
    product: 'Mechanical Keyboard',
    quantity: 1,
    price: 149.99,
    orderDate: '2024-01-17',
    total: 149.99,
  },
]

type Identifiable = { id: string | number }

const columns = [
  { accessorKey: 'orderNumber', header: 'Order #', size: 90 },
  { accessorKey: 'customer', header: 'Customer', size: 150 },
  { accessorKey: 'email', header: 'Email', size: 200 },
  { accessorKey: 'product', header: 'Product', size: 180 },
  {
    accessorKey: 'quantity',
    header: 'Qty',
    size: 80,
    meta: { className: 'text-center' },
  },
  {
    accessorKey: 'price',
    header: 'Price',
    size: 100,
    cell: ({ getValue }) => `$${Number(getValue()).toFixed(2)}`,
    meta: { className: 'text-right' },
  },
  {
    accessorKey: 'orderDate',
    header: 'Order Date',
    size: 120,
    cell: ({ getValue }) => new Date(String(getValue())).toLocaleDateString(),
  },
  {
    accessorKey: 'total',
    header: 'Total',
    size: 100,
    cell: ({ getValue }) => `$${Number(getValue()).toFixed(2)}`,
    meta: { className: 'text-right font-medium' },
  },
  {
    id: 'actions',
    header: 'Actions',
    size: 120,
    enableSorting: false,
    meta: { className: 'text-center' },
    cell: () =>
      h('div', { class: 'flex justify-center gap-1' }, [
        h(
          UiButton,
          { variant: 'ghost', size: 'icon', class: 'size-8', type: 'button' },
          { default: () => h(Pencil, { class: 'size-4 text-primary-foreground' }) },
        ),
        h(
          UiButton,
          { variant: 'ghost', size: 'icon', class: 'size-8', type: 'button' },
          { default: () => h(Trash2, { class: 'size-4 text-destructive-foreground' }) },
        ),
      ]),
  },
] as ColumnDef<Identifiable, unknown>[]
</script>

<template>
  <div class="w-full">
    <UiDataTable
      :columns="columns"
      :data="data"
      :column-pinning="{ left: ['orderNumber'], right: ['actions'] }"
    />
  </div>
</template>
