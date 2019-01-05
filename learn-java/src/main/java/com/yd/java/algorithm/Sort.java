package com.yd.java.algorithm;

import java.util.stream.Stream;

/**
 * 排序算法
 *
 * @author Yd on  2018-08-30
 * @description
 **/
public class Sort {

    /**
     * 冒泡排序
     * <p>
     * 1、比较相邻的元素。如果第一个比第二个大，就交换它们两个；
     * 2、对每一对相邻元素作同样的工作，从开始第一对到结尾的最后一对，这样在最后的元素应该会是最大的数；
     * 3、针对所有的元素重复以上的步骤，除了最后一个；
     * 4、重复步骤1~3，直到排序完成。
     * </p>
     *
     * @param arr
     * @return
     */
    public static int[] bubbleSort(int[] arr) {
        //这里不进行非空校验
        for (int i = 0; i < arr.length - 1; i++) { //只需要冒泡 N-1 次
            for (int j = 0; j < arr.length - 1 - i; j++) { //每冒泡一次，后面比较的次数减1
                if (arr[j] < arr[j + 1]) {        // 相邻元素两两对比
                    int temp = arr[j + 1];        // 元素交换
                    arr[j + 1] = arr[j];
                    arr[j] = temp;
                }
            }
        }
        return arr;
    }

    /**
     * <p>
     * 首先在未排序序列中找到最小（大）元素，存放到排序序列的起始位置，
     * 然后，再从剩余未排序元素中继续寻找最小（大）元素，然后放到已排序序列的末尾。
     * 以此类推，直到所有元素均排序完毕。
     * </p>
     *
     * @param arr
     * @return
     */
    public static int[] selectionSort(int[] arr) {
        int minIndex, temp;
        for (int i = 0; i < arr.length - 1; i++) {
            minIndex = i;
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[minIndex] < arr[j]) {
                    minIndex = j;
                }
            }
            temp = arr[i];
            arr[i] = arr[minIndex];
            arr[minIndex] = temp;
        }
        return arr;
    }

    /**
     * 插入排序
     * 通过构建有序序列，对于未排序数据，在已排序序列中从后向前扫描，找到相应位置并插入
     * <p>
     * 1、从第一个元素开始，该元素可以认为已经被排序；
     * 2、取出下一个元素，在已经排序的元素序列中从后向前扫描；
     * 3、如果该元素（已排序）大于新元素，将该元素移到下一位置；
     * 4、重复步骤3，直到找到已排序的元素小于或者等于新元素的位置；
     * 5、将新元素插入到该位置后；
     * 6、重复步骤2~5
     * </p>
     *
     * @param arr
     * @return
     */
    public static int[] insertionSort(int[] arr) {
        int preIndex, current;
        for (int i = 1; i < arr.length; i++) {
            preIndex = i - 1;
            current = arr[i];
            while (preIndex >= 0 && arr[preIndex] > current) {
                arr[preIndex + 1] = arr[preIndex];
                preIndex--;
            }
            arr[preIndex + 1] = current;
        }
        return arr;
    }

    /**
     * 希尔排序
     * 简单插入排序的改进版。它与插入排序的不同之处在于，它会优先比较距离较远的元素。希尔排序又叫缩小增量排序。
     * <p>
     * 1、选择一个增量序列t1，t2，…，tk，其中ti>tj，tk=1；
     * 2、按增量序列个数k，对序列进行k 趟排序；
     * 3、每趟排序，根据对应的增量ti，将待排序列分割成若干长度为m 的子序列，分别对各子表进行直接插入排序。
     * 仅增量因子为1 时，整个序列作为一个表来处理，表长度即为整个序列的长度。
     * </p>
     *
     * @param arr
     * @return
     */
    public static int[] shellSort(int[] arr) {
        //增量gap，并逐步缩小增量
        for (int gap = arr.length / 2; gap > 0; gap /= 2) {
            //从第gap个元素，逐个对其所在组进行直接插入排序操作
            for (int i = gap; i < arr.length; i++) {
                int j = i;
                int temp = arr[j];
                if (arr[j] < arr[j - gap]) {
                    while (j - gap >= 0 && temp < arr[j - gap]) {
                        //移动法
                        arr[j] = arr[j - gap];
                        j -= gap;
                    }
                    arr[j] = temp;
                }
            }
        }
        return arr;
    }


    /**
     * 堆排序
     * <p>
     * a.将无需序列构建成一个堆，根据升序降序需求选择大顶堆或小顶堆;
     * <p>
     * 　　b.将堆顶元素与末尾元素交换，将最大元素"沉"到数组末端;
     * <p>
     * 　　c.重新调整结构，使其满足堆定义，然后继续交换堆顶元素与当前末尾元素，反复执行调整+交换步骤，直到整个序列有序。
     * </p>
     *
     * @param arr
     */
    public static void HeapSort(int[] arr) {
        //1.构建大顶堆
        for (int i = arr.length / 2 - 1; i >= 0; i--) {
            //从第一个非叶子结点从下至上，从右至左调整结构
            adjustHeap(arr, i, arr.length);
        }
        int temp;
        //2.调整堆结构+交换堆顶元素与末尾元素
        for (int j = arr.length - 1; j > 0; j--) {
            //swap(arr, 0, j);//将堆顶元素与末尾元素进行交换
            temp = arr[0];
            arr[0] = arr[j];
            arr[j] = temp;

            adjustHeap(arr, 0, j);//重新对堆进行调整
        }

    }

    /**
     * 调整大顶堆（仅是调整过程，建立在大顶堆已构建的基础上）
     *
     * @param arr
     * @param i
     * @param length
     */
    public static void adjustHeap(int[] arr, int i, int length) {
        int temp = arr[i];//先取出当前元素i
        for (int k = i * 2 + 1; k < length; k = k * 2 + 1) {//从i结点的左子结点开始，也就是2i+1处开始
            if (k + 1 < length && arr[k] < arr[k + 1]) {//如果左子结点小于右子结点，k指向右子结点
                k++;
            }
            if (arr[k] > temp) {//如果子节点大于父节点，将子节点值赋给父节点（不用进行交换）
                arr[i] = arr[k];
                i = k;
            } else {
                break;
            }
        }
        arr[i] = temp;//将temp值放到最终的位置
    }


    /**
     * 归并排序
     * <p>建立在归并操作上的一种有效的排序算法。该算法是采用分治法（Divide and Conquer）的一个非常典型的应用。
     * 将已有序的子序列合并，得到完全有序的序列；即先使每个子序列有序，再使子序列段间有序。若将两个有序表合并成一个有序表，称为2-路归并。
     * <p>
     * 1、把长度为n的输入序列分成两个长度为n/2的子序列；
     * 2、对这两个子序列分别采用归并排序；
     * 3、将两个排序好的子序列合并成一个最终的排序序列。
     * </p>
     *
     * @param arr
     * @return
     */
    public static int[] mergeSort(int[] arr) {
        int[] temp = new int[arr.length];//在排序前，先建好一个长度等于原数组长度的临时数组，避免递归中频繁开辟空间
        mergeSort(arr, 0, arr.length - 1, temp);
        return arr;
    }

    /**
     * 递归式将无序数组拆分成最小有序数组
     *
     * @param arr
     * @param left
     * @param right
     * @param temp
     */
    private static void mergeSort(int[] arr, int left, int right, int[] temp) {
        if (left < right) {
            int mid = (left + right) / 2;
            mergeSort(arr, left, mid, temp);//左边归并排序，使得左子序列有序
            mergeSort(arr, mid + 1, right, temp);//右边归并排序，使得右子序列有序
            merge(arr, left, mid, right, temp);//将两个有序子数组合并操作
        }
    }


    /**
     * 将两个有序的数组 合并
     *
     * @param arr
     * @param left
     * @param mid
     * @param right
     * @param temp
     */
    private static void merge(int[] arr, int left, int mid, int right, int[] temp) {
        int i = left;//左序列指针
        int j = mid + 1;//右序列指针
        int t = 0;//临时数组指针
        while (i <= mid && j <= right) {
            if (arr[i] <= arr[j]) {
                temp[t++] = arr[i++];
            } else {
                temp[t++] = arr[j++];
            }
        }
        while (i <= mid) {//将左边剩余元素填充进temp中
            temp[t++] = arr[i++];
        }
        while (j <= right) {//将右序列剩余元素填充进temp中
            temp[t++] = arr[j++];
        }
        t = 0;
        //将temp中的元素全部拷贝到原数组中
        while (left <= right) {
            arr[left++] = temp[t++];
        }
    }


    public static void quickSort(int a[],int left,int right)
    {
        int dp;
        if(left<right)
        {
            dp=partition(a,left,right);
            quickSort(a,left,dp-1);
            quickSort(a,dp+1,right);
        }
    }

    public static int partition(int a[],int left,int right)
    {
        int i=left;
        int j=right;
        int temp=a[i];
        while(i<j)
        {
            while(i<j && a[j]>=temp)
                j--;
            if(i<j)
                a[i]=a[j];
            while(i<j && a[i]<=temp)
                i++;
            if(i<j)
                a[j]=a[i];
        }
        a[i]=temp;
        return i;
    }

    public static void main(String[] args) {
        int[] arr = new int[]{2, 5, 1, 1, 3, 4};
//        bubbleSort(arr);
//        selectionSort(arr);
//        insertionSort(arr);
        quickSort(arr,0,arr.length-1);
        Stream.of(arr).skip(1).forEach(System.out::print);

    }

}
