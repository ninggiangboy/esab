<script setup lang="ts">
import { docFormToast } from '@/docs/examples/_internal/docFormSubmit'
import { toTypedSchema } from '@vee-validate/zod'
import { z } from 'zod'
import { Button } from '@/ui/components/button'
import { DatePicker } from '@/ui/components/date-picker'
import { BsTimeField } from '@/ui/components/datefield'
import {
  Form,
  FormControl,
  FormField,
  FormItem,
  FormLabel,
  FormMessage,
  useForm,
} from '@/ui/components/form'

const schema = toTypedSchema(z.object({ appointmentDate: z.any(), appointmentTime: z.any() }))
const { handleSubmit } = useForm({ validationSchema: schema })
const onSubmit = handleSubmit((v) => docFormToast(v))
</script>
<template>
  <Form class="flex w-full max-w-lg flex-col gap-4" @submit="onSubmit">
    <div class="flex gap-2">
      <div class="min-w-0 flex-1">
        <FormField v-slot="{ componentField }" name="appointmentDate">
          <FormItem>
            <FormLabel>Appointment date</FormLabel>
            <FormControl v-slot="controlProps">
              <DatePicker v-bind="{ ...componentField, ...controlProps }" />
            </FormControl>
            <FormMessage />
          </FormItem>
        </FormField>
      </div>
      <FormField v-slot="{ componentField }" name="appointmentTime">
        <FormItem>
          <FormLabel class="opacity-0">Time</FormLabel>
          <FormControl v-slot="controlProps">
            <BsTimeField class="w-[72px]" v-bind="{ ...componentField, ...controlProps }" />
          </FormControl>
          <FormMessage />
        </FormItem>
      </FormField>
    </div>
    <Button type="submit">Submit</Button>
  </Form>
</template>
