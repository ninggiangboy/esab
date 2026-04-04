<script setup lang="ts">
import { BsSelect } from '@/ui/components/select'
import { languageOptions } from './selectLanguages'
</script>

<template>
  <div class="w-full space-y-4">
    <BsSelect searchable :options="languageOptions" />
  </div>
</template>
