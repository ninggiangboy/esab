<script setup lang="ts">
import { Label } from '@/ui/components/field'
import DocDateRangePicker from '@/docs/examples/_internal/DocDateRangePicker.vue'
</script>
<template>
  <div class="grid w-full max-w-[320px] gap-1.5">
    <Label>Trip dates</Label>
    <DocDateRangePicker />
  </div>
</template>
