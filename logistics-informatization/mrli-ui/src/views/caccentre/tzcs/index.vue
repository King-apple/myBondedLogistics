<!--
  -    Copyright (c) 2018-2025, mrli All rights reserved.
  -
  - Redistribution and use in source and binary forms, with or without
  - modification, are permitted provided that the following conditions are met:
  -
  - Redistributions of source code must retain the above copyright notice,
  - this list of conditions and the following disclaimer.
  - Redistributions in binary form must reproduce the above copyright
  - notice, this list of conditions and the following disclaimer in the
  - documentation and/or other materials provided with the distribution.
  - Neither the name of the pig4cloud.com developer nor the names of its
  - contributors may be used to endorse or promote products derived from
  - this software without specific prior written permission.
  - Author: mrli
  -->
<template>
  <div class="caccentre-weather-index">
    <basic-container>
      <div class="root">
        <el-row>
          <el-col :span="12">
            <SlickList
              :lockToContainerEdges="true"
              lockAxis="yx"
              v-model="items"
              tag="ul"
            >
              <SlickItem
                v-for="(item, index) in items"
                :index="index"
                :key="item.key"
                tag="li"
              >
                {{ item.name }}
              </SlickItem>
            </SlickList>
          </el-col>
          <el-col :span="12">
            <SlickList
              :lockToContainerEdges="true"
              lockAxis="yx"
              v-model="items"
              tag="ul"
            >
              <SlickItem
                v-for="(item, index) in items2"
                :index="index"
                :key="item.key"
                tag="li"
              >
                {{ item.name }}
              </SlickItem>
            </SlickList>
          </el-col>
        </el-row>
      </div>
    </basic-container>
  </div>
</template>

<script>
import { mapGetters } from "vuex";
import { SlickList, SlickItem } from "vue-slicksort";
export default {
  components: { SlickList, SlickItem },
  name: "caccentre-weather-index",
  data() {
    return {
      items: [
        { name: "Item1", key: 1 },
        { name: "Item2", key: 2 },
        { name: "Item3", key: 3 },
        { name: "Item4", key: 4 },
        { name: "Item5", key: 5 }
      ],
      items2: [
        { name: "Item6", key: 6 },
        { name: "Item7", key: 7 },
        { name: "Item8", key: 8 },
        { name: "Item9", key: 9 },
        { name: "Item10", key: 10 }
      ]
    };
  },
  computed: {
    ...mapGetters(["permissions"])
  },
  mounted() {},
  created() {},
  methods: {}
};
</script>
<style lang="scss" scoped>
</style>