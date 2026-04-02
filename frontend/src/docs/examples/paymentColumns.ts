import type { ColumnDef } from '@tanstack/vue-table'

import type { PaymentRow } from '@/lib/docs/payments-demo'

export function paymentColumns(): ColumnDef<PaymentRow, unknown>[] {
  return [
    { accessorKey: 'email', header: 'Email', size: 270 },
    { accessorKey: 'transactionDate', header: 'Transaction Date' },
    { accessorKey: 'paymentReference', header: 'Payment Reference' },
  ]
}
