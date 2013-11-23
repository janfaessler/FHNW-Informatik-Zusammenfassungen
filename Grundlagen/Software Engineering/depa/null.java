public class NullLayout implements LayoutManager {
	public void addLayoutComponent(String name, Component comp) {} public void removeLayoutComponent(Component comp) {}
	public Dimension minimumLayoutSize(Container parent){
		return parent.getSize();
	}
	public Dimension preferredLayoutSize(Container parent){
		return parent.getSize();
	}
	public void layoutContainer(Container parent) {}
}