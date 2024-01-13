<template>
  <div class="container" ref="container">测试用</div>
</template>

<script>
import LogicFlow from "@logicflow/core";
import "@logicflow/core/dist/style/index.css";
import ProcessNode from "./graph/ProcessNode.js";
import "./graph/style.css";

export default {
  data() {
    return {
      lf: null,
      types: {
        0: "报销发起",
        1: "业务审批",
        2: "出纳付款",
        3: "流程转发",
        4: "报销完成",
      },
      states: {
        "-1": "通过失败",
        0: "未操作",
        1: "已通过",
      },
    };
  },
  mounted() {
    const sheetId = this.$route.params.sheetId; // 路径中带有报销单id
    this.$store.state.sheetId = sheetId;

    // 获取流程节点列表
    this.getReimburseProcessNodeList(sheetId);

    this.lf = new LogicFlow({
      container: this.$refs.container,
      grid: true,
    });

    this.lf.register(ProcessNode);
    this.lf.render({
      nodes: [],
      edges: [],
    });

    // 监听hover事件
    this.lf.on("node:mouseenter", (model) => {
      var data = model.data;
      this.lf.setProperties(data.id, {
        isHover: "true",
      });
    });
    this.lf.on("node:mouseleave", (model) => {
      var data = model.data;
      this.lf.setProperties(data.id, {
        isHover: "false",
      });
      console.log(model);
    });
  },
  methods: {
    // 获取报销单的流程节点列表
    getReimburseProcessNodeList(sheetId) {
      var x = 200;
      var y = 100;
      this.axios
        .get("/reimburse/process/list", {
          params: {
            sheetId: sheetId,
          },
        })
        .then((response) => {
          var processNodeList = response.data;
          for (let i = 0; i < processNodeList.length; i++) {
            let processNode = processNodeList[i];
            // var text =
            //   this.types[processNode.type] + "-" + processNode.oprUserName;

            // 添加节点
            this.lf.addNode({
              id: String(processNode.id),
              type: "process-node", // 暂时使用rect，待修改为自定义类型
              x: x,
              y: y,
              // text: text,
              properties: {
                // 待自定义类型使用
                type: this.types[processNode.type],
                state: processNode.state,
                feedback: processNode.feedback || "暂无反馈信息",
                oprUser: processNode.oprUserName,
                isHover: "false",
              },
            });
            x = Number(x + 210);
          }
          for (let i = 0; i < processNodeList.length; i++) {
            let processNode = processNodeList[i];

            // 最后一个节点，不用添加新边
            if (
              processNode.isLast === 1 ||
              i === Number(processNodeList.length) - 1
            ) {
              break;
            }
            if (processNode.type === 3) {
              // 添加边，需判断是否流程转发
              // 流程转发，添加多一条虚线
              let num = Number(i) + 2;
              if (num < processNodeList.length) {
                this.lf.addEdge({
                  type: "polyline",
                  sourceNodeId: String(processNode.id),
                  targetNodeId: String(processNodeList[num].id),
                });
              }
            }
            // 一条实线指向下一节点
            let num = Number(i) + 1;
            this.lf.addEdge({
              type: "polyline",
              sourceNodeId: String(processNode.id),
              targetNodeId: String(processNodeList[num].id),
            });
          }
        });
    },
  },
};
</script>

<style scoped>
.container {
  width: 100%;
  height: 500px;
}
</style>
