<script setup lang="ts">
import type { ColumnDef } from '@tanstack/vue-table'
import type { RowSelectionState } from '@tanstack/vue-table'
import { CreditCard, Pencil, Trash2 } from 'lucide-vue-next'
import { computed, h, ref, watch } from 'vue'
import { toast } from 'vue-sonner'

import UiButton from '@/components/ui/UiButton.vue'
import UiDataTable from '@/components/ui/UiDataTable.vue'
import UiNativeSelect from '@/components/ui/UiNativeSelect.vue'
import UiPagination from '@/components/ui/UiPagination.vue'
import UiSearchField from '@/components/ui/UiSearchField.vue'
import type { DataTableSorting } from '@/components/ui/data-table-utils'
import { confirm } from '@/lib/confirm-dialog'
import { cn } from '@/lib/utils'

type Method = 'debit_card' | 'credit_card' | 'bank_transfer' | 'paypal'

interface PayRow {
  id: string
  paymentMethod: Method
  email: string
  transactionDate: string
  paymentReference: string
}

const methodBadge: Record<Method, string> = {
  debit_card: 'text-green-600 bg-green-500/10',
  credit_card: 'text-blue-600 bg-blue-500/10',
  bank_transfer: 'text-yellow-600 bg-yellow-500/10',
  paypal: 'text-purple-600 bg-purple-500/10',
}

const methodLabel: Record<Method, string> = {
  debit_card: 'Debit Card',
  credit_card: 'Credit Card',
  bank_transfer: 'Bank Transfer',
  paypal: 'Paypal',
}

const allRows: PayRow[] = [
  {
    id: '1',
    paymentMethod: 'debit_card',
    email: 'a@example.com',
    transactionDate: '2024-01-10',
    paymentReference: 'PAY-001',
  },
  {
    id: '2',
    paymentMethod: 'credit_card',
    email: 'b@example.com',
    transactionDate: '2024-01-11',
    paymentReference: 'PAY-002',
  },
  {
    id: '3',
    paymentMethod: 'paypal',
    email: 'c@example.com',
    transactionDate: '2024-01-12',
    paymentReference: 'PAY-003',
  },
  {
    id: '4',
    paymentMethod: 'bank_transfer',
    email: 'd@example.com',
    transactionDate: '2024-01-13',
    paymentReference: 'PAY-004',
  },
  {
    id: '5',
    paymentMethod: 'credit_card',
    email: 'alice@example.com',
    transactionDate: '2024-02-01',
    paymentReference: 'PAY-005',
  },
  {
    id: '6',
    paymentMethod: 'debit_card',
    email: 'bob@example.com',
    transactionDate: '2024-02-02',
    paymentReference: 'PAY-006',
  },
]

const search = ref('')
const methodFilter = ref('')
const sorting = ref<DataTableSorting | null>(null)
const page = ref(1)
const pageSize = ref(3)
const rowSelection = ref<RowSelectionState>({ '2': true })

const filtered = computed(() => {
  let rows = [...allRows]
  if (methodFilter.value) {
    rows = rows.filter((r) => r.paymentMethod === methodFilter.value)
  }
  if (search.value.trim()) {
    const q = search.value.toLowerCase()
    rows = rows.filter((r) => r.email.toLowerCase().includes(q))
  }
  const s = sorting.value
  if (s?.sortBy) {
    const { sortBy, sortDirection } = s
    rows.sort((a, b) => {
      const av = String(a[sortBy as keyof PayRow] ?? '')
      const bv = String(b[sortBy as keyof PayRow] ?? '')
      const c = av.localeCompare(bv)
      return sortDirection === 'desc' ? -c : c
    })
  }
  return rows
})

const pageCount = computed(() => Math.max(1, Math.ceil(filtered.value.length / pageSize.value)))

const pageData = computed(() => {
  const start = (page.value - 1) * pageSize.value
  return filtered.value.slice(start, start + pageSize.value)
})

watch([filtered, pageSize], () => {
  page.value = Math.min(page.value, pageCount.value)
})

type Identifiable = { id: string | number }

const columns = [
  {
    accessorKey: 'paymentMethod',
    header: 'Payment method',
    cell: ({ getValue }) => {
      const value = getValue() as Method
      return h('div', { class: 'flex items-center gap-2' }, [
        h('div', { class: cn(methodBadge[value], 'rounded-sm p-1.5') }, [
          h(CreditCard, { class: 'size-4' }),
        ]),
        h('span', { class: 'font-medium' }, methodLabel[value]),
      ])
    },
  },
  { accessorKey: 'email', header: 'Email', size: 220 },
  { accessorKey: 'transactionDate', header: 'Transaction date' },
  { accessorKey: 'paymentReference', header: 'Reference' },
  {
    id: 'actions',
    header: 'Actions',
    size: 96,
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

function clearFilters() {
  search.value = ''
  methodFilter.value = ''
  page.value = 1
}

function onMethodChange(ev: Event) {
  methodFilter.value = (ev.target as HTMLSelectElement).value
  page.value = 1
}

function onPageSizeChange(ev: Event) {
  pageSize.value = Number((ev.target as HTMLSelectElement).value)
  page.value = 1
}

const selectedCount = computed(() => Object.keys(rowSelection.value).length)

function handleDeleteSelected() {
  const count = selectedCount.value
  confirm({
    variant: 'destructive',
    title: 'Delete Users',
    description: 'Are you sure you want to delete these users?',
    action: {
      label: 'Delete',
      onClick: () => {
        toast.success('Users Deleted Successfully', {
          description: `Deleted ${count} selected users`,
        })
      },
    },
  })
}
</script>

<template>
  <div class="w-full space-y-3">
    <div class="flex flex-wrap items-end gap-2">
      <div class="w-full min-w-[180px] max-w-[280px]">
        <UiSearchField v-model="search" placeholder="Search email…" />
      </div>
      <UiNativeSelect :value="methodFilter" class="w-[200px]" @change="onMethodChange">
        <option value="">All methods</option>
        <option value="debit_card">Debit Card</option>
        <option value="credit_card">Credit Card</option>
        <option value="bank_transfer">Bank Transfer</option>
        <option value="paypal">Paypal</option>
      </UiNativeSelect>
      <UiButton v-if="search || methodFilter" variant="outline" type="button" @click="clearFilters">
        Clear filters
      </UiButton>
      <UiButton
        v-if="selectedCount"
        class="ml-auto max-sm:hidden"
        variant="destructive"
        type="button"
        @click="handleDeleteSelected"
      >
        Delete Selected
      </UiButton>
    </div>
    <div class="flex h-8 items-center justify-between text-sm text-muted-foreground">
      <span>{{ selectedCount }} selected · {{ filtered.length }} rows</span>
    </div>
    <UiDataTable
      v-model:row-selection="rowSelection"
      v-model:sorting="sorting"
      enable-row-selection
      enable-sorting
      :columns="columns"
      :data="pageData"
      :column-pinning="{ left: [], right: ['actions'] }"
      container-class-name="min-h-[200px]"
    />
    <div class="flex flex-wrap items-center justify-between gap-3 border-t pt-3">
      <div class="flex items-center gap-2 text-sm">
        <span class="text-muted-foreground">Rows per page</span>
        <UiNativeSelect class="w-20" :value="String(pageSize)" @change="onPageSizeChange">
          <option value="3">3</option>
          <option value="5">5</option>
          <option value="10">10</option>
        </UiNativeSelect>
      </div>
      <UiPagination v-model="page" :page-count="pageCount" />
    </div>
  </div>
</template>
