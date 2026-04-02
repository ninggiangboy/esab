/** Small static payment rows for docs data-table demos (client-side sort/filter). */
export interface PaymentRow {
  id: string
  email: string
  transactionDate: string
  paymentReference: string
}

export const PAYMENT_ROWS: PaymentRow[] = [
  {
    id: '1',
    email: 'alice@example.com',
    transactionDate: '2024-03-01',
    paymentReference: 'PAY-10001',
  },
  {
    id: '2',
    email: 'bob@example.com',
    transactionDate: '2024-03-15',
    paymentReference: 'PAY-10002',
  },
  {
    id: '3',
    email: 'carol@example.com',
    transactionDate: '2024-02-28',
    paymentReference: 'PAY-10003',
  },
  {
    id: '4',
    email: 'dan@example.com',
    transactionDate: '2024-04-01',
    paymentReference: 'PAY-10004',
  },
  {
    id: '5',
    email: 'erin@example.com',
    transactionDate: '2024-01-20',
    paymentReference: 'PAY-10005',
  },
]
