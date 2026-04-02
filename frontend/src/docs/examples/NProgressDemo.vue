<script setup lang="ts">
import { useQuery } from '@tanstack/vue-query'
import { watch } from 'vue'
import NProgress from 'nprogress'

import UiButton from '@/components/ui/UiButton.vue'
import UiSpinner from '@/components/ui/UiSpinner.vue'

const { isFetching, refetch } = useQuery({
  queryKey: ['docs-nprogress'],
  queryFn: () => new Promise<boolean>((resolve) => setTimeout(() => resolve(true), 1000)),
  enabled: false,
})

watch(isFetching, (f) => {
  if (f) NProgress.start()
  else NProgress.done()
})
</script>

<template>
  <div>
    <UiButton variant="outline" :disabled="isFetching" @click="() => refetch()">
      <UiSpinner v-if="isFetching" class="size-4" />
      Refetch
    </UiButton>
  </div>
</template>
