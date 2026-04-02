<script setup lang="ts">
import UiTable from '@/components/ui/UiTable.vue'
import UiTableBody from '@/components/ui/UiTableBody.vue'
import UiTableCaption from '@/components/ui/UiTableCaption.vue'
import UiTableCell from '@/components/ui/UiTableCell.vue'
import UiTableHead from '@/components/ui/UiTableHead.vue'
import UiTableHeader from '@/components/ui/UiTableHeader.vue'
import UiTableRow from '@/components/ui/UiTableRow.vue'

const invoices = [
  {
    invoice: 'INV001',
    paymentStatus: 'Paid',
    totalAmount: '$250.00',
    paymentMethod: 'Credit Card',
  },
  { invoice: 'INV002', paymentStatus: 'Pending', totalAmount: '$150.00', paymentMethod: 'PayPal' },
  {
    invoice: 'INV003',
    paymentStatus: 'Unpaid',
    totalAmount: '$350.00',
    paymentMethod: 'Bank Transfer',
  },
]
</script>

<template>
  <div class="w-full overflow-x-auto">
    <UiTable>
      <UiTableCaption>A list of your recent invoices.</UiTableCaption>
      <UiTableHeader>
        <UiTableRow>
          <UiTableHead class="w-[100px]">Invoice</UiTableHead>
          <UiTableHead>Status</UiTableHead>
          <UiTableHead>Method</UiTableHead>
          <UiTableHead class="text-right">Amount</UiTableHead>
        </UiTableRow>
      </UiTableHeader>
      <UiTableBody>
        <UiTableRow v-for="row in invoices" :key="row.invoice">
          <UiTableCell class="font-semibold">{{ row.invoice }}</UiTableCell>
          <UiTableCell>{{ row.paymentStatus }}</UiTableCell>
          <UiTableCell>{{ row.paymentMethod }}</UiTableCell>
          <UiTableCell class="text-right">{{ row.totalAmount }}</UiTableCell>
        </UiTableRow>
      </UiTableBody>
    </UiTable>
  </div>
</template>
