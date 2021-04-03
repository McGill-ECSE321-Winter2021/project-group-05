<template>
  <div id="container">
    <AdminHeader id="header"/>
    <!---- technician select--->
    <div id="technician__container">
      <select v-model="technicianEmail"  class="custom-select custom-select-sm">
        <option v-bind:key="technician.email"
                v-for="technician in allTechnicians" v-bind:value="technician.email" selected>
          {{technician.email}}
        </option>
      </select>
    </div>

    <div id="dateAndService__container">
      <div id="date__container">
        <VueCtkDateTimePicker v-bind:format="'YYYY-MM-DD hh:mm a'"
                              v-model="date" />
      </div>
    </div>

    <!-----assign button -->
    <div id="button__container">
      <button @click="createTimeSlotAndAssign(technicianEmail)" type="button"
              class="btn  btn-outline-primary btn-lg btn-block">
        Create Availability for {{technicianEmail}}
      </button>
    </div>

    <div id="calendar" >
      <button @click="minCellWidth = minCellWidth ? 0 : 400">
        {{ minCellWidth ? 'min cell width: 400px' : 'Add min cell width' }}
      </button>
      <button @click="minSplitWidth = minSplitWidth ? 0 : 200">
        {{ minSplitWidth ? 'min split width: 200px' : 'Add min split width' }}
      </button>
      <button @click="stickySplitLabels = !stickySplitLabels">
        Sticky Split Labels
      </button>
      <button @click="splitDays[1].hide = !splitDays[1].hide">
        Show/Hide Dad
      </button>

      <vue-cal
               :time-from="8 * 60"
               :time-step="30"
               :disable-views="['years', 'year', 'month']"
               editable-events
               :events="events"
               :split-days="splitDays"
               :sticky-split-labels="stickySplitLabels"
               :min-cell-width="minCellWidth"
               :min-split-width="minSplitWidth">
      </vue-cal>

    </div>

  </div>


</template>

<script src="./TimeSlotPage.js">

</script>

<style scoped>
#technician__container{
  margin-bottom: 16px;
}

#calendar{
  text-align: center;
  margin:5% auto;
  margin-left: 10px;


}

</style>
