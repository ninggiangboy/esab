<script setup lang="ts">
import UiNativeSelect from '@/components/ui/UiNativeSelect.vue'
</script>

<template>
  <UiNativeSelect multiple class="min-h-[96px] font-medium">
    <option class="text-blue-600" value="blue">Blue team</option>
    <option class="text-rose-600" value="red">Red team</option>
    <option class="text-emerald-600" value="green">Green team</option>
  </UiNativeSelect>
</template>
