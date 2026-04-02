<script setup lang="ts">
import type { ColumnDef } from '@tanstack/vue-table'

import UiDataTable from '@/components/ui/UiDataTable.vue'
import { PAYMENT_ROWS } from '@/lib/docs/payments-demo'

import { paymentColumns } from './paymentColumns'

type Identifiable = { id: string | number }

const columns = paymentColumns() as ColumnDef<Identifiable, unknown>[]
</script>

<template>
  <div class="w-full">
    <UiDataTable :columns="columns" :data="PAYMENT_ROWS" />
  </div>
</template>
