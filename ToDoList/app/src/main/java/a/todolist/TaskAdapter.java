package a.todolist;

import java.util.List;
import java.util.Locale;

import java.text.SimpleDateFormat;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import a.todolist.database.TaskEntity;
/* TaskAdapter создает ViewHolders, которые содержат описание и приоритет задачи,
   и связывает его с RecyclerView для эффективного отображения данных.
*/
public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {
    // константа для определения формата даты
    private static final String DATE_FORMAT = "dd/MM/yyy";
    // переменная слушатель для обработки выбранного элемента списка
    final private ItemClickListener itemClickListener;
    // экземпляр класса List, который содержит список дел и объект класса Context
    private List<TaskEntity> taskEntities;
    private Context context;
    // создание экземпляра форматированной даты
    private SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT, Locale.getDefault());
    /* Конструктор адаптера TaskAdapter, который инициализирует
     * @param context  текущий контекст
     * @param listener слушателя выбора элемента списка
     */
    public TaskAdapter(Context context, ItemClickListener listener) {
        this.context = context;
        itemClickListener = listener;
    }
    /* создание ViewHolders для каждого видимого элемента списка */
    @Override
    public TaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // заполнение представления элемента списка
        View view = LayoutInflater.from(context)
                .inflate(R.layout.task_layout, parent, false);
        return new TaskViewHolder(view);
    }
    /* метод связывает данные списка с представлением на экране */
    @Override
    public void onBindViewHolder(TaskViewHolder holder, int position) {
        // определение значений выбранного элемента
        TaskEntity taskEntity = taskEntities.get(position);
        String description = taskEntity.getDescription();
        int priority = taskEntity.getPriority();
        String updatedAt = dateFormat.format(taskEntity.getUpdatedAt());
        // вывод значений на экран
        holder.taskDescriptionView.setText(description);
        holder.updatedAtView.setText(updatedAt);
        // значение приоритета заполняет TextView
        String priorityString = "" + priority; // converts int to String
        holder.priorityView.setText(priorityString);
    }
    /* Вспомогательный метод, который определяет цвет, соответствующий приоритету:
	1 = red, 2 = orange, 3 = yellow */
    private int getPriorityColor(int priority) {
        int priorityColor = 0;

        switch (priority) {
            case 1:
                priorityColor = ContextCompat.getColor(context, R.color.materialRed);
                break;
            case 2:
                priorityColor = ContextCompat.getColor(context, R.color.materialOrange);
                break;
            case 3:
                priorityColor = ContextCompat.getColor(context, R.color.materialYellow);
                break;
            default:
                break;
        }
        return priorityColor;
    }

    /* метод возвращает количество элементов в списке */
    @Override
    public int getItemCount() {
        if (taskEntities == null) {
            return 0;
        }
        return taskEntities.size();
    }
    /* метод получает список */
    public List<TaskEntity> getTasks() { return taskEntities; }
    /* метод обновляет данные в списке, если были внесены изменения */
    public void setTasks(List<TaskEntity> taskEntities) {
        this.taskEntities = taskEntities;
        notifyDataSetChanged();
    }
    // реализация интерфейса при выборе элемента из списка
    public interface ItemClickListener {
        void onItemClickListener(int itemId);
    }
    /* Внутренний класс, который расширяет класс RecyclerView.ViewHolder и
       реализует интерфейс View.OnClickListener при выборе элемента списка
    */
    class TaskViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // переменные класса для текстовых полей представления
        TextView taskDescriptionView;
        TextView updatedAtView;
        TextView priorityView;
        /* конструктор классаe TaskViewHolders */
        public TaskViewHolder(View itemView) {
            super(itemView);

            taskDescriptionView = itemView.findViewById(R.id.taskDescription);
            updatedAtView = itemView.findViewById(R.id.taskUpdatedAt);
            priorityView = itemView.findViewById(R.id.priorityTextView);
            itemView.setOnClickListener(this);
        }
        /* метод обработки выбора элемента и определения позиции выбранного элемента в БД */
        @Override
        public void onClick(View view) {
            int elementId = taskEntities.get(getAdapterPosition()).getId();
            itemClickListener.onItemClickListener(elementId);
        }
    }
}