<script setup lang="ts">
import UiNativeSelect from '@/components/ui/UiNativeSelect.vue'
</script>

<template>
  <UiNativeSelect multiple disabled class="min-h-[80px] opacity-70">
    <option value="a" selected>A</option>
    <option value="b" selected>B</option>
  </UiNativeSelect>
</template>
