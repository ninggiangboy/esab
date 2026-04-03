<script setup lang="ts">
import { CircleX, Info } from 'lucide-vue-next'
import { Button } from '@/ui/components/button'
import {
  Dialog,
  DialogContent,
  DialogDescription,
  DialogFooter,
  DialogHeader,
  DialogTitle,
} from '@/ui/components/dialog'
import { closeConfirm, confirmDialogData, confirmDialogOpen } from '@/ui/lib/confirmDialog'
</script>

<template>
  <Dialog v-model:open="confirmDialogOpen">
    <DialogContent v-if="confirmDialogData" class="sm:max-w-md" :close-button="false">
      <DialogHeader>
        <div class="flex items-start gap-3">
          <component
            :is="confirmDialogData.variant === 'destructive' ? CircleX : Info"
            class="size-6 shrink-0 text-muted-foreground"
          />
          <div class="grid gap-1">
            <DialogTitle>{{ confirmDialogData.title }}</DialogTitle>
            <DialogDescription v-if="confirmDialogData.description">
              {{ confirmDialogData.description }}
            </DialogDescription>
          </div>
        </div>
      </DialogHeader>
      <DialogFooter class="gap-2 sm:gap-0">
        <Button
          v-if="confirmDialogData.cancel"
          variant="outline"
          @click="
            () => {
              confirmDialogData?.cancel?.onClick()
              closeConfirm()
            }
          "
        >
          {{ confirmDialogData.cancel.label }}
        </Button>
        <Button
          v-if="confirmDialogData.action"
          :variant="confirmDialogData.variant === 'destructive' ? 'destructive' : 'default'"
          @click="
            async () => {
              await confirmDialogData?.action?.onClick()
              closeConfirm()
            }
          "
        >
          {{ confirmDialogData.action.label }}
        </Button>
      </DialogFooter>
    </DialogContent>
  </Dialog>
</template>
