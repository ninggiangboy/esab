<script setup lang="ts">
import { computed } from 'vue'
import { Button } from '@/ui/components/button'
import { BsSelect, type BsSelectOption } from '@/ui/components/select'
import { cn } from '@/ui/lib/utils'

const props = defineProps<{
  page: number
  pageSize: number
  total: number
  pageSizeOptions?: BsSelectOption[]
  class?: string
}>()

const emit = defineEmits<{
  'update:page': [p: number]
  'update:pageSize': [s: number]
}>()

const pageCount = computed(() => Math.max(1, Math.ceil(props.total / props.pageSize)))

const sizeOptions = computed(
  () => props.pageSizeOptions ?? [10, 20, 50].map((n) => ({ id: String(n), name: `${n} / page` })),
)

const pageSizeModel = computed({
  get: () => String(props.pageSize),
  set: (v) => emit('update:pageSize', Number(v)),
})
</script>

<template>
  <div :class="cn('flex flex-wrap items-center justify-between gap-3', props.class)">
    <div class="flex items-center gap-2 text-sm text-muted-foreground">
      <span>Rows per page</span>
      <BsSelect v-model="pageSizeModel" class="w-32" :options="sizeOptions" />
    </div>
    <div class="flex items-center gap-2">
      <Button
        variant="outline"
        size="sm"
        :disabled="page <= 1"
        @click="emit('update:page', page - 1)"
      >
        Prev
      </Button>
      <span class="text-sm tabular-nums">{{ page }} / {{ pageCount }}</span>
      <Button
        variant="outline"
        size="sm"
        :disabled="page >= pageCount"
        @click="emit('update:page', page + 1)"
      >
        Next
      </Button>
    </div>
  </div>
</template>
