<script setup lang="ts">
import { docFormToast } from '@/docs/examples/_internal/docFormSubmit'
import { toTypedSchema } from '@vee-validate/zod'
import { z } from 'zod'
import { Button } from '@/ui/components/button'
import { DatePicker } from '@/ui/components/date-picker'
import {
  Form,
  FormField,
  FormItem,
  FormLabel,
  FormMessage,
  FormVm,
  useForm,
} from '@/ui/components/form'

const schema = toTypedSchema(z.object({ date: z.any() }))
const { handleSubmit } = useForm({ validationSchema: schema })
const onSubmit = handleSubmit((v) => docFormToast(v))
</script>
<template>
  <Form class="w-full max-w-[280px] space-y-4" @submit="onSubmit">
    <FormField v-slot="{ componentField }" name="date">
      <FormItem>
        <FormLabel>Date</FormLabel>
        <FormVm generic="string | undefined" v-slot="vm" :component-field="componentField">
          <DatePicker v-bind="vm" />
        </FormVm>
        <FormMessage />
      </FormItem>
    </FormField>
    <Button type="submit">Submit</Button>
  </Form>
</template>
